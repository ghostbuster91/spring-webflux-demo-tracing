./gradlew bootJar

wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer

java -javaagent:dd-java-agent.jar \
	-Ddd.logs.injection=true \
	-Ddd.service=http-example \
	-Ddd.env=test \
	-Ddd.trace.enabled=true \
	-Ddd.trace.annotation.async=true \
	-Ddd.trace.propagation.style="b3multi" \
	-jar build/libs/*.jar
