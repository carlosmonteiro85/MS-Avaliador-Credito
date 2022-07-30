
rabbitmq : docker run -it --rm --name cursorabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management

keycloak: docker run -p 8081:8080 -e KEYCLOAK_ADMIN=guest -e KEYCLOAK_ADMIN_PASSWORD=guest quay.io/keycloak/keycloak:18.0.2 start-dev
