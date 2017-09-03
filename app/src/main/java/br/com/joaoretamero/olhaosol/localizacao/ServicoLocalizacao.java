package br.com.joaoretamero.olhaosol.localizacao;


import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import rx.Observable;

public class ServicoLocalizacao {

    private FusedLocationProviderClient fusedLocationClient;

    public ServicoLocalizacao(FusedLocationProviderClient fusedLocationClient) {
        this.fusedLocationClient = fusedLocationClient;
    }

    @SuppressWarnings("MissingPermission")
    public Observable<Boolean> getDisponibilidadeLocalizacao() {
        return Observable.create(emitter ->
            fusedLocationClient.getLocationAvailability()
                .addOnSuccessListener(locationAvailability -> {
                    boolean isLocationAvailable = locationAvailability.isLocationAvailable();
                    emitter.onNext(isLocationAvailable);
                    emitter.onCompleted();
                })
                .addOnFailureListener(error -> emitter.onError(error)));
    }

    public Observable<Location> getLocalizacao() {
        return Observable.create(new LocalizacaoObservable(fusedLocationClient));
    }
}
