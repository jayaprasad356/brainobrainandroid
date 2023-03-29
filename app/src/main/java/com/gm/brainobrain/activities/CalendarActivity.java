package com.gm.brainobrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gm.brainobrain.DateSelectedListener;
import com.gm.brainobrain.R;
import com.gm.brainobrain.adapter.HwAdapter;
import com.gm.brainobrain.adapter.StaticsAdapter;
import com.gm.brainobrain.helper.ApiConfig;
import com.gm.brainobrain.helper.Constant;
import com.gm.brainobrain.helper.Session;
import com.gm.brainobrain.model.HomeCollection;
import com.gm.brainobrain.model.StaticsData;
import com.gm.brainobrain.model.XmlRecords;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity {
    Activity activity;


    private static GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private static int currentMonth;
    static String selectedGridDate;
    private java.util.Calendar cmonth;
    static int cutmonth;
    private GridView gridview;
    Uri bitmapUri;
    DateSelectedListener dateSelectedListener;
    private String loadXmlFile;
    private ArrayList<XmlRecords> records;
    private boolean changed;
    private int clickedDate;
    private String dateFormat;
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


    private TextView tv_month;
    RecyclerView recyclerView;
    Session session;
    private String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dateSelectedListener = selectedDate -> {
            Toast.makeText(activity, selectedDate, Toast.LENGTH_SHORT).show();
            calenderStatistics(selectedDate);
        };
        activity = CalendarActivity.this;
        session = new Session(activity);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();

        //apicall();

        //**********2019 Holidays **************

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(activity, cal_month, HomeCollection.date_collection_arr, dateSelectedListener);
        currentMonth = cal_month.get(GregorianCalendar.MONTH);
        cutmonth = cal_month.get(GregorianCalendar.MONTH);


        tv_month = findViewById(R.id.tv_month);
        ImageButton previousMonth = findViewById(R.id.ib_prev);
        ImageButton nextMonth = findViewById(R.id.Ib_next);

        tv_month.setText(month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR));
        loadXmlFile = month[currentMonth] + "_" + calendar.get(Calendar.YEAR);
        if (cal_month.get(GregorianCalendar.YEAR) == 2021) {
            dateFormat = calendar.get(Calendar.DATE) + " - " + month[currentMonth] + " - " + calendar.get(Calendar.YEAR) + " - స్వస్తిశ్రీ ప్లవ";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth < 3) {
            dateFormat = calendar.get(Calendar.DATE) + " - " + month[currentMonth] + " - " + calendar.get(Calendar.YEAR) + " - స్వస్తిశ్రీ ప్లవ";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth > 3) {
            dateFormat = calendar.get(Calendar.DATE) + " - " + month[currentMonth] + " - " + calendar.get(Calendar.YEAR) + " - శుభకృతు";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth < 3 && calendar.get(Calendar.DATE) < 21) {
            dateFormat = calendar.get(Calendar.DATE) + " - " + month[currentMonth] + " - " + calendar.get(Calendar.YEAR) + " - శుభకృతు";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth > 2) {
            dateFormat = calendar.get(Calendar.DATE) + " - " + month[currentMonth] + " - " + calendar.get(Calendar.YEAR) + " -  శోభకృతు";
        }


        clickedDate = (calendar.get(Calendar.DATE) - 1);
        selectedGridDate = "";

        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/arlrb.TTF");

        tv_month.setTypeface(typeface);


        Typeface typefaceText = Typeface.createFromAsset(activity.getAssets(), "fonts/sree.ttf");

        gridview = (GridView) findViewById(R.id.gv_calendar);


        gridViewSet();
        parseXML();


        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.YEAR) > 2020) {
                    if (currentMonth == 10 && cal_month.get(GregorianCalendar.YEAR) == 2021) {
                        Toast.makeText(activity, "ప్రదర్శించడానికి డేటా లేదు", Toast.LENGTH_SHORT).show();

                    } else {
                        setPreviousMonth();
                        allFunc();
                    }
                }
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.YEAR) < 2024) {
                    if (currentMonth == 11 && cal_month.get(GregorianCalendar.YEAR) == 2023) {
                        Toast.makeText(activity, "ప్రదర్శించడానికి డేటా లేదు", Toast.LENGTH_SHORT).show();

                    } else {
                        setNextMonth();
                        allFunc();

                    }
                }
            }
        });

        Log.d("TOKEN_API", session.getData(Constant.TOKEN));


        calenderApi();

    }

    private void calenderApi() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("CALENDER_API", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject holeData = jsonObject.getJSONObject("data");
                    JSONArray data = holeData.getJSONArray("data");
                    Log.d("CALAENDER_VAL", data + "");
                    System.out.println(holeData);

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject uniqueData = data.getJSONObject(i);
                        boolean status = uniqueData.getBoolean("data_exist");
                        String date = "";
//                        String desc = status ? "true" : "false";
                        date = uniqueData.getString("date");
                        HomeCollection.date_collection_arr.add(new HomeCollection(date, "bhogi", status + ""));

                    }
                    for (int i = 0; i < HomeCollection.date_collection_arr.size(); i++) {
                        if (HomeCollection.date_collection_arr.get(i).date.equals("2023-01-08")) {
                            Log.d("CALAENDER_STA", HomeCollection.date_collection_arr.get(i).description + "" + i);
                        }
                    }

                    gridview.setAdapter(hwAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, activity, Constant.CALENDER_STATS_URL, params, true, 0);

    }

    private void calenderStatistics(String selectedDate) {
        System.out.println(selectedDate);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE, selectedDate);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("CALENDER_API", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray(Constant.DATA);
                    Log.d("CALAENDER_VAL", data + "");
                    Gson g = new Gson();
                    ArrayList<StaticsData> staticsData = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject uniqueData = data.getJSONObject(i);
                        if (uniqueData != null) {
                            StaticsData group = g.fromJson(uniqueData.toString(), StaticsData.class);
                            staticsData.add(group);
                        } else {
                            break;
                        }
                    }
                    StaticsAdapter adapter = new StaticsAdapter(staticsData, activity);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, activity, Constant.CALENDER_STATS_DAY_URL, params, true, 1);

    }

    private void apicall() {
        String authKey = "3|s5f5XrwsClIc43aSAgAsT4q3e9Btxxs8CcrpVoq9";
        String url = "https://demo.trainingzone.in/api/user/stats-calendar";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Response responsse = gson.fromJson(response, Response.class);
                        System.out.println(responsse);
                        ArrayList<String> d = new ArrayList<>();
                        System.out.println(d);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject holeData = jsonObject.getJSONObject("data");
                            JSONArray data = holeData.getJSONArray("data");
                            System.out.println(holeData);


                            for (int i = 0; i < data.length(); i++) {
                                JSONObject uniqueData = data.getJSONObject(i);
                                boolean status = uniqueData.getBoolean("data_exist");
                                String date = "";
                                String desc = status ? "true" : "false";
                                date = uniqueData.getString("date");
                                HomeCollection.date_collection_arr.add(new HomeCollection(date, "bhogi", desc));
                            }
                            gridview.setAdapter(hwAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Do something with the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authKey);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    private void allFunc() {
        refreshCalendar();
        gridViewSet();
        clickedDate = 0;
        selectedGridDate = HwAdapter.day_string.get(HwAdapter.firstDay - 1);
        if (cal_month.get(GregorianCalendar.YEAR) == 2021) {
            dateFormat = 1 + " - " + month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR) + " - స్వస్తిశ్రీ ప్లవ";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth < 3) {
            dateFormat = 1 + " - " + month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR) + " - స్వస్తిశ్రీ ప్లవ";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth > 2) {
            dateFormat = 1 + " - " + month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR) + " -  శుభకృతు";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth < 3) {
            dateFormat = 1 + " - " + month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR) + " -  శుభకృతు";

        } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth > 2) {
            dateFormat = 1 + " - " + month[currentMonth] + " - " + cal_month.get(GregorianCalendar.YEAR) + " -  శోభకృతు";
        }
        hwAdapter.notifyDataSetChanged();
        loadXmlFile = month[currentMonth] + "_" + cal_month.get(GregorianCalendar.YEAR);
        parseXML();
    }

    private void gridViewSet() {

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedGridDate = HwAdapter.day_string.get(position);
                String[] separatedTime = HwAdapter.day_string.get(position).split("-");

                String cl_day = separatedTime[2].replaceFirst("^0*", "");
                String cl_month = separatedTime[1].replaceFirst("^0*", "");
                String cl_year = separatedTime[0].replaceFirst("^0*", "");

                clickedDate = (Integer.parseInt(cl_day) - 1);


                if (cal_month.get(GregorianCalendar.YEAR) == 2021) {
                    dateFormat = cl_day + " - " + month[(Integer.parseInt(cl_month) - 1)] + " - " + cl_year + " -  ప్లవ";

                } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth < 3) {
                    dateFormat = cl_day + " - " + month[(Integer.parseInt(cl_month) - 1)] + " - " + cl_year + " -  ప్లవ";

                } else if (cal_month.get(GregorianCalendar.YEAR) == 2022 && currentMonth > 3) {
                    dateFormat = cl_day + " - " + month[(Integer.parseInt(cl_month) - 1)] + " - " + cl_year + " -  శుభకృతు";

                } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth < 3 && clickedDate < 21) {
                    dateFormat = cl_day + " - " + month[(Integer.parseInt(cl_month) - 1)] + " - " + cl_year + " -  శుభకృతు";

                } else if (cal_month.get(GregorianCalendar.YEAR) == 2023 && currentMonth > 1 && clickedDate > 20) {
                    dateFormat = cl_day + " - " + month[(Integer.parseInt(cl_month) - 1)] + " - " + cl_year + " -  శోభకృతు";
                }
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, activity);
                hwAdapter.notifyDataSetChanged();
                if (loadXmlFile.equals(month[(Integer.parseInt(cl_month) - 1)] + "_" + Integer.parseInt(cl_year))) {
                    //printRecords();

                } else {
                    loadXmlFile = month[(Integer.parseInt(cl_month) - 1)] + "_" + Integer.parseInt(cl_year);
                    parseXML();
                }
            }
        });
    }

    protected void setNextMonth() {
        currentMonth++;
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            currentMonth = 0;
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);

        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) + 1);
        }
        tv_month.setText(month[currentMonth] + " - " + (cal_month.get(GregorianCalendar.YEAR)));

    }

    protected void setPreviousMonth() {
        currentMonth--;
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            currentMonth = 11;
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);

        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
        tv_month.setText(month[currentMonth] + " - " + (cal_month.get(GregorianCalendar.YEAR)));
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = parserFactory.newPullParser();
            try {
                InputStream inputStream = activity.getAssets().open("Months/" + loadXmlFile + ".xml");
                pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                pullParser.setInput(inputStream, null);
                processParser(pullParser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void processParser(XmlPullParser pullParser) throws IOException, XmlPullParserException {
        records = new ArrayList<>();
        int eventType = pullParser.getEventType();

        XmlRecords currentRecord = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = "null";
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = pullParser.getName();

                    if ("record".equals(eltName)) {
                        currentRecord = new XmlRecords();
                        records.add(currentRecord);

                    } else if (currentRecord != null) {
                        if ("date".equals(eltName)) {
                            currentRecord.Date = pullParser.nextText();

                        } else if ("sunrise".equals(eltName)) {
                            currentRecord.Sunrise = pullParser.nextText();

                        } else if ("sunset".equals(eltName)) {
                            currentRecord.Sunset = pullParser.nextText();

                        } else if ("moonrise".equals(eltName)) {
                            currentRecord.Moonrise = pullParser.nextText();

                        } else if ("moonset".equals(eltName)) {
                            currentRecord.Moonset = pullParser.nextText();

                        } else if ("rutuvu".equals(eltName)) {
                            currentRecord.Ruthu = pullParser.nextText();

                        } else if ("masam".equals(eltName)) {
                            currentRecord.Masam = pullParser.nextText();

                        } else if ("paksham".equals(eltName)) {
                            currentRecord.Paksham = pullParser.nextText();

                        } else if ("kalam".equals(eltName)) {
                            currentRecord.Kalam = pullParser.nextText();

                        } else if ("thidi".equals(eltName)) {
                            currentRecord.Thidi = pullParser.nextText();

                        } else if ("vaara".equals(eltName)) {
                            currentRecord.Week = pullParser.nextText();

                        } else if ("nakshatra".equals(eltName)) {
                            currentRecord.Nakshatram = pullParser.nextText();

                        } else if ("yogam".equals(eltName)) {
                            currentRecord.Yogam = pullParser.nextText();

                        } else if ("karana".equals(eltName)) {
                            currentRecord.Karanam = pullParser.nextText();

                        } else if ("rahu".equals(eltName)) {
                            currentRecord.Rahuv = pullParser.nextText();

                        } else if ("yamag".equals(eltName)) {
                            currentRecord.Yama = pullParser.nextText();

                        } else if ("varjyam".equals(eltName)) {
                            currentRecord.Varjyam = pullParser.nextText();

                        } else if ("gulika".equals(eltName)) {
                            currentRecord.Gulika = pullParser.nextText();

                        } else if ("dhurmuhu".equals(eltName)) {
                            currentRecord.Dhurmuhurth = pullParser.nextText();

                        } else if ("festival".equals(eltName)) {
                            currentRecord.Festival = pullParser.nextText();

                        }
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        printRecords();
    }

    private void printRecords() {
        String date = (clickedDate + 1) + "";
        String strCurrentDate = loadXmlFile + "_" + date;
        SimpleDateFormat format = new SimpleDateFormat("MMMM_yyyy_dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("yyyy-MM-dd");
        String cadate = format.format(newDate);
        Log.d("CURRENTDATE", "" + cadate);
        //panchangamApi(cadate);
        // panchangamList(cadate);

        String festival;

        if (records.get(clickedDate).Festival == null) {
            festival = "";
        } else {
            festival = records.get(clickedDate).Festival;
        }
        String htmlData;

        if (!changed) {
            changed = true;
            htmlData = "<html><style type='text/css'>@font-face { font-family: sree; src: url('fonts/sree.ttf'); } body p {font-family: sree;}</style><head><meta name='viewport' user-scalable=no' /></head><body align='center' style='padding: 0' >" +
                    "<div style='color:#0F1970;text-align:center;font-size:19;font-family: sree;'>" + dateFormat + "</div>" +

                    "<div style='font-weight:bold;text-align:left;margin-left:10px;line-height:1.5;font-size:15;font-family: sree;'>" +

                    "<span style='color:#006600'>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "తిథి " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Thidi + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "వారము " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Week + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "నక్షత్రం " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Nakshatram + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "యోగం " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Yogam + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "కరణం " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Karanam + "</div>" + "</span>" + "<br>" +
                    "<span style='color:#d31d8c'>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "రాహుకాలం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Rahuv + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "యమగండము" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Yama + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "వర్జ్యం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Varjyam + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "గుళికా " + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Gulika + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "దుర్ముహుర్తం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Dhurmuhurth + "</div>" + "</span>" + "<br>" +

                    "<div style='color:#0F1970;font-weight:bold;margin-top:7px;text-align:center;font-size:17;font-family: sree;'>" +
                    "* &nbsp; " + festival + "&nbsp; *" + "<br>" + "</div>" +
                    "</body></html>";

        } else {
            changed = false;
            htmlData = "<html><style type='text/css'>@font-face { font-family: sree; src: url('fonts/sree.ttf'); } body p {font-family: sree;}</style><head><meta name='viewport' user-scalable=no' /></head><body align='center' style='padding: 0' >" +
                    "<div style='color:#0F1970;text-align:center;font-size:19;font-family: sree;'>" + dateFormat + "</div>" +

                    "<div style='font-weight:bold;text-align:left;margin-left:10px;line-height:1.5;font-size:15;font-family: sree;'>" +
                    "<span style='color:#006600'>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "తిథి " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Thidi + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "వారము " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Week + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "నక్షత్రం" + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Nakshatram + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "యోగం " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Yogam + "</div>" + "<br>" +
                    "<div style='width:20%;text-align:left;float:left'>" + "కరణం " + "</div>" +
                    "<div style='width:80%;text-align:left;float:left'>" + ": &nbsp;" + records.get(clickedDate).Karanam + "</div>" + "</span>" + "<br>" +
                    "<span style='color:#d31d8c'>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "రాహుకాలం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Rahuv + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "యమగండము" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Yama + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "వర్జ్యం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Varjyam + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "గుళికా " + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Gulika + "</div>" + "<br>" +
                    "<div style='width:26%;text-align:left;float:left'>" + "దుర్ముహుర్తం" + "</div>" +
                    "<div style='width:74%;text-align:left;float:left'>" + ":&nbsp;" + records.get(clickedDate).Dhurmuhurth + "</div>" + "</span>" + "<br>" +

                    "<div style='color:#0F1970;font-weight:bold;margin-top:7px;text-align:center;font-size:17;font-family: sree;'>" +
                    "* &nbsp;" + festival + "&nbsp; *" + "<br>" + "</div>" +

                    "</body></html>";
        }
    }


}