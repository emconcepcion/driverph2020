package com.cav.DriverphTruckerlearningPH2020;


import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Advisory extends AppCompatActivity {

    TextView tesda_link, dot_link, lto_link, ltfrb_link, mmda_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisory);

        dot_link = findViewById(R.id.dot_link);
        dot_link.setText("Visit website:\n https://dotr.gov.ph/");
        Linkify.addLinks(dot_link, Linkify.WEB_URLS);

        lto_link = findViewById(R.id.lto_link);
        lto_link.setText("Visit website:\n https://www.lto.gov.ph/");
        Linkify.addLinks(lto_link, Linkify.WEB_URLS);

        ltfrb_link = findViewById(R.id.ltfrb_link);
        ltfrb_link.setText("Visit website:\n https://ltfrb.gov.ph/");
        Linkify.addLinks(ltfrb_link, Linkify.WEB_URLS);

        tesda_link = findViewById(R.id.tesda_link);
        tesda_link.setText("Visit website:\n https://www.tesda.gov.ph/");
        Linkify.addLinks(tesda_link, Linkify.WEB_URLS);

        mmda_link = findViewById(R.id.mmda_link);
        mmda_link.setText("Visit website:\n https://mmda.gov.ph/");
        Linkify.addLinks(mmda_link, Linkify.WEB_URLS);

    }
}