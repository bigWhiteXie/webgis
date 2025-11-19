# 项目部署
请先在环境中安装好docker

## 数据库部署
导入镜像

cd database
```
docker load -i my-postgis.tar
```

启动容器
```
docker run -d  --name my-postgis  -e POSTGRES_PASSWORD=123456  -p 54321:5432  my-postgis:latest
```
如果要挂载数据目录在宿主机
```angular2html
docker run -d --name my-postgis -e POSTGRES_PASSWORD=123456 -p 54321:5432 -v C:/docker-data/postgis-data:/var/lib/postgresql/data my-postgis:latest
```
## 后端应用部署
**重要:**

**目录切换到项目根目录**

导入镜像:
```
docker load -i webgis.tar
```
运行容器
```
docker run -d \
  -p 8081:8081 \
  -e SPRING_DATASOURCE_DRUID_MASTER_URL=jdbc:postgresql://127.0.0.1:54321/HydroVision \
  -e SPRING_DATASOURCE_DRUID_MASTER_USERNAME=postgres \
  -e SPRING_DATASOURCE_DRUID_MASTER_PASSWORD=j3391111 \
  --network=host \
  webgis
```
