package me.weicools.material.component.module.panel.expansion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import me.weicools.material.component.R;
import me.weicools.material.component.config.RouterPath;

/**
 * @author Weicools create on 2018.08.23
 *
 * desc:
 */
@Route(path = RouterPath.MODULE_EXPANSION_PANEL_BASIC)
public class ExpansionPanelBasicActivity extends AppCompatActivity {

  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expansion_panel_basic);
  }
}