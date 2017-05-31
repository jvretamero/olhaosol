package br.com.joaoretamero.olhaosol.util.schedulers;


import rx.Scheduler;

public interface ProvedorScheduler {

    Scheduler io();

    Scheduler ui();
}
