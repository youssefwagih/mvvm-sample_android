package com.youssef.weather.ui.weather;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.youssef.weather.R;
import com.youssef.weather.repository.model.WeatherResponse;
import com.youssef.weather.ui.photos.PhotosActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
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
                // reset imageview
                iv.setImageBitmap(null);

                // take required permission for capturing photo and saving it
                Dexter.withActivity(getActivity())
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .onSameThread()
                        .check();
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

        weatherViewModel.getFileSaveStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(getContext(), R.string.save_success, Toast.LENGTH_LONG).show();
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

    // this for Android APIs before Android O
    void getScreenShot(View screenView) {
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        bitmap = screenView.getDrawingCache();

        setImageView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void getBitmapFromView(View view, Activity activity) {
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

    private void setImageView() {
        iv.setImageBitmap(bitmap);
        try {
            weatherViewModel.savePhoto(bitmap);
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
}
