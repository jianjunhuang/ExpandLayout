package com.jianjunhuang.demo.expandlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button showBtn;
  private ExpandableLinearLayout expandableLinearLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    showBtn = findViewById(R.id.show);
    expandableLinearLayout = findViewById(R.id.expand);
    showBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        expandableLinearLayout.toggle();
      }
    });
  }
}
