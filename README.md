# 项目部署
请先在环境中安装好docker

## 数据库部署
导入镜像

cd database
```
docker load -i gisdata-image.tar
```

启动容器
```
docker run -d  --name my-postgis  -e POSTGRES_PASSWORD=j3391111  -p 54321:5432  my-postgis:latest
```

## 后端应用部署
**重要:**

**目录切换到项目根目录**

导入镜像:
```
docker load -i webgis-image.tar
```
运行容器
```
docker run -d \
  -p 8081:8081 \
  -e SPRING_DATASOURCE_DRUID_MASTER_URL=jdbc:postgresql://新的数据库地址:端口/数据库名 \
  -e SPRING_DATASOURCE_DRUID_MASTER_USERNAME=用户名 \
  -e SPRING_DATASOURCE_DRUID_MASTER_PASSWORD=密码 \
  webgis-app

```