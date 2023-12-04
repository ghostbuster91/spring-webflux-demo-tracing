package org.example;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Bean
    public OpenTelemetry create() {
        Resource resource = Resource.getDefault().toBuilder().build();
        SdkTracerProvider sdkTracerProvider =
                SdkTracerProvider.builder()
                        .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                        .setResource(resource)
                        .build();

        SdkMeterProvider sdkMeterProvider =
                SdkMeterProvider.builder()
                        .registerMetricReader(
                                PeriodicMetricReader.builder(LoggingMetricExporter.create()).build())
                        .setResource(resource)
                        .build();

        SdkLoggerProvider sdkLoggerProvider =
                SdkLoggerProvider.builder()
                        .addLogRecordProcessor(
                                BatchLogRecordProcessor.builder(SystemOutLogRecordExporter.create()).build())
                        .setResource(resource)
                        .build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setMeterProvider(sdkMeterProvider)
                .setLoggerProvider(sdkLoggerProvider)
                .buildAndRegisterGlobal();
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
