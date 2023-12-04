./gradlew bootJar

if [ ! -f opentelemetry-javaagent.jar ]; then
	echo "Downloading dd-java-agent"
	wget -O opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
else
	echo "Skipping dd-java-agent download"
fi

env OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317 java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name="http-example" \
  -Dotel.resource.attributes="env=test" \
  -Dotel.propagators="b3multi" \
	-jar build/libs/*.jar
