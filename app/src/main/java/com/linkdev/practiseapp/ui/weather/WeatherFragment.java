package com.linkdev.practiseapp.ui.weather;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkdev.practiseapp.R;
import com.linkdev.practiseapp.repository.model.WeatherResponse;
import com.linkdev.practiseapp.ui.photos.PhotosActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WeatherFragment extends Fragment {
    private static final int MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST = 1;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    ImageView weatherIcon;
    FrameLayout fl;
    ImageView iv;
    Button captureBtn;
    Button photosBtn;

    private Bitmap bitmap = null;
    private WeatherViewModel weatherViewModel;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        cityField = (TextView) view.findViewById(R.id.city_field);
        updatedField = (TextView) view.findViewById(R.id.updated_field);
        detailsField = (TextView) view.findViewById(R.id.details_field);
        currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        weatherIcon = (ImageView) view.findViewById(R.id.weather_icon);
        fl = (FrameLayout) view.findViewById(R.id.fl);
        iv = (ImageView) view.findViewById(R.id.iv);
        captureBtn = (Button) view.findViewById(R.id.captureBtn);
        photosBtn = (Button) view.findViewById(R.id.photosBtn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageBitmap(null);
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        photosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PhotosActivity.createIntent(getContext());
                startActivity(intent);
            }
        });

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getCurrentWeatherData().observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {
                showWeatherData(weatherResponse);
            }
        });
    }

    private void showWeatherData(WeatherResponse weatherResponse) {
        cityField.setText(weatherResponse.getSys().getCountry());

        detailsField.setText(
                String.format("%s\nHumidity: %d%%\nPressure: %d hPa", weatherResponse.getWeather().get(0).getDescription().toUpperCase(Locale.US), weatherResponse.getMain().getHumidity(), weatherResponse.getMain().getPressure()));

        currentTemperatureField.setText(weatherResponse.getMain().getTemp() + " ℃");

        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(weatherResponse.getDt() * 1000));
        updatedField.setText("Last update: " + updatedOn);


        Picasso.with(getContext()).load(String.format("http://openweathermap.org/img/w/%s.png", weatherResponse.getWeather().get(0).getIcon())).into(weatherIcon);
    }

    //deprecated version
    /*  Method which will return Bitmap after taking screenshot. We have to pass the view which we want to take screenshot.  */
    void getScreenShot(View screenView) {
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        bitmap = screenView.getDrawingCache();

        setImageView();
    }

    void getBitmapFromView(View view, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            int[] locationOfViewInWindow = new int[2];
            view.getLocationInWindow(locationOfViewInWindow);
            Rect rect = new Rect(locationOfViewInWindow[0], locationOfViewInWindow[1],
                    locationOfViewInWindow[0] + view.getWidth(),
                    locationOfViewInWindow[1] + view.getHeight());

            PixelCopy.request(activity.getWindow(), rect, bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                @Override
                public void onPixelCopyFinished(int copyResult) {
                    if (copyResult == PixelCopy.SUCCESS) {
                        setImageView();
                    }
                }
            }, new Handler());
        }
    }

    private void setImageView() {
        iv.setImageBitmap(bitmap);
        try {
            savebitmap(bitmap, getContext());
            Toast.makeText(getContext(), R.string.save_success, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                //  Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            BitmapDrawable ob = new BitmapDrawable(getResources(), photo);
            fl.setBackground(ob);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getBitmapFromView(fl, getActivity());
            } else {
                getScreenShot(fl);
            }
        }
        //
    }

    public static File savebitmap(Bitmap bmp, Context context) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        Date currentTime = Calendar.getInstance().getTime();

        File mydirf = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (!mydirf.exists())
            mydirf.mkdir();

        File f = new File(mydirf, currentTime.toString() + ".jpg");

        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

}
