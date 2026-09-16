services:
  # 1. 데이터베이스 (MariaDB)
  db:
    image: mariadb:latest
    container_name: yj-db-cicd
    restart: always
    environment:
      MARIADB_ROOT_PASSWORD: "1234"
      MARIADB_DATABASE: user_db
    ports:
      - "3326:3306"

  # 2. 백엔드 (Spring Boot)
  backend:
    environment:
      - SPRING_PROFILES_ACTIVE=docker
    build: ./backend
    container_name: yj-backend-cicd
    ports:
      - "8090:8080"
    depends_on:
      - db

  # 3. 프론트엔드 (React)
  frontend:
    build: ./frontend
    container_name: yj-frontend-cicd
    ports:
      - "63352:80" # 호스트의 63352 포트를 컨테이너의 80 포트와 연결 (원본 63342, IntelliJ 내장서버와 충돌 방지)
    depends_on:
      - backend