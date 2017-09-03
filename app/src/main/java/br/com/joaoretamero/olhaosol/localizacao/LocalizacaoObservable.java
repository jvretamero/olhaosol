package br.com.joaoretamero.olhaosol.localizacao;


import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

class LocalizacaoObservable implements Observable.OnSubscribe<Location> {

    private FusedLocationProviderClient fusedLocationClient;

    LocalizacaoObservable(FusedLocationProviderClient fusedLocationClient) {
        this.fusedLocationClient = fusedLocationClient;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void call(Subscriber<? super Location> subscriber) {
        LocationRequest locationRequest = criaLocationRequest();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location localizacao = locationResult.getLastLocation();
                if (localizacao != null) {
                    subscriber.onNext(locationResult.getLastLocation());
                }
                subscriber.onCompleted();
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

        subscriber.add(Subscriptions.create(
            () -> fusedLocationClient.removeLocationUpdates(locationCallback)));
    }

    @NonNull
    private LocationRequest criaLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        return locationRequest;
    }
}
