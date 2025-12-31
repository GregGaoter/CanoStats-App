package ch.epicerielacanopee.statistics.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class AnalysisProgressService {

  private final Sinks.Many<Integer> progressSink = Sinks.many().multicast().onBackpressureBuffer();

  public Flux<Integer> getProgressStream() {
    return progressSink.asFlux();
  }

  public void emitProgress(int progress) {
    progressSink.tryEmitNext(progress);
  }

  public void complete() {
    progressSink.tryEmitComplete();
  }
}
