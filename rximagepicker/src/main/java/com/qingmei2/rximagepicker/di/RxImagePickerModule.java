package com.qingmei2.rximagepicker.di;

import android.content.Context;

import com.qingmei2.rximagepicker.core.IImagePickerProcessor;
import com.qingmei2.rximagepicker.core.ImagePickerConfigProcessor;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.delegate.ProxyTranslator;
import com.qingmei2.rximagepicker.di.scheduler.IRxImagePickerSchedulers;
import com.qingmei2.rximagepicker.di.scheduler.RxImagePickerSchedulers;
import com.qingmei2.rximagepicker.ui.ICameraPickerView;
import com.qingmei2.rximagepicker.ui.IGalleryPickerView;

import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 * {@link RxImagePickerModule} be used from {@link RxImagePickerComponent}
 */
@Module
public final class RxImagePickerModule {

    private final Map<String, ICameraPickerView> cameraViews;
    private final Map<String, IGalleryPickerView> galleryViews;
    private final Context context;

    public RxImagePickerModule(RxImagePicker.Builder builder) {
        this.cameraViews = builder.getCameraViews();
        this.galleryViews = builder.getGalleryViews();
        this.context = builder.getRootContext();
    }

    @Provides
    Map<String, ICameraPickerView> providesCameraViews() {
        return cameraViews;
    }

    @Provides
    Map<String, IGalleryPickerView> provideGalleryViews() {
        return galleryViews;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    IImagePickerProcessor providesRxImagePickerProcessor(Context context,
                                                         Map<String, IGalleryPickerView> galleryViews,
                                                         Map<String, ICameraPickerView> cameraViews,
                                                         IRxImagePickerSchedulers schedulers) {
        return new ImagePickerConfigProcessor(
                context,
                cameraViews,
                galleryViews,
                schedulers
        );
    }

    @Provides
    ProxyTranslator proxyTranslator(Map<String, IGalleryPickerView> galleryViews,
                                    Map<String, ICameraPickerView> cameraViews) {
        return new ProxyTranslator(galleryViews, cameraViews);
    }

    @Provides
    IRxImagePickerSchedulers providesRxSchedulers() {
        return new RxImagePickerSchedulers();
    }
}