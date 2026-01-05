package ch.epicerielacanopee.statistics.service;

import org.springframework.stereotype.Service;

import ch.epicerielacanopee.statistics.service.dto.AnalysisProgressEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class AnalysisProgressService {

  private final Sinks.Many<AnalysisProgressEvent> progressSink = Sinks.many().multicast().onBackpressureBuffer();

  public Flux<AnalysisProgressEvent> getProgressStream() {
    return progressSink.asFlux();
  }

  public void emitProgress(int progress, String message) {
    progressSink.tryEmitNext(new AnalysisProgressEvent(progress, message));
  }

  public void complete() {
    progressSink.tryEmitComplete();
  }
}
