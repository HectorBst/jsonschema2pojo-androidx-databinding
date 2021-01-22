package dev.hctbst.jsonschema2pojo.androidx.databinding.example;

import android.os.Bundle;
import android.widget.NumberPicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import dev.hctbst.jsonschema2pojo.androidx.databinding.example.data.ExampleEntity;
import dev.hctbst.jsonschema2pojo.androidx.databinding.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.setEntity(new ExampleEntity());
		NumberPicker np = findViewById(R.id.oneInteger_edit);
		np.setMinValue(0);
		np.setMaxValue(10);
	}
}
