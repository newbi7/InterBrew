# 기본 이미지로 Node.js 공식 이미지 사용
FROM node:14 as build-stage

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드 복사
COPY . .

# 의존성 설치
RUN npm install

# 프로덕션용 빌드
RUN npm run build

# 배포를 위한 두 번째 단계
FROM nginx:stable-alpine as production-stage

# Nginx 설정 파일 복사
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# 빌드된 애플리케이션 파일 복사
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 컨테이너가 80번 포트로 들어오는 요청을 처리하도록 설정
EXPOSE 8080

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
