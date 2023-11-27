./gradlew bootJar

if [ ! -f dd-java-agent.jar ]; then
	echo "Downloading dd-java-agent"
	wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer
else
	echo "Skipping dd-java-agent download"
fi

java -javaagent:dd-java-agent.jar \
	-Ddd.logs.injection=true \
	-Ddd.service=http-example \
	-Ddd.env=test \
	-Ddd.trace.enabled=true \
	-Ddd.trace.annotation.async=true \
	-Ddd.trace.propagation.style="b3multi" \
	-jar build/libs/*.jar
