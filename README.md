# 项目部署
请先在环境中安装好docker，并设置代理
linux
```
{
  "registry-mirrors": [
    "https://docker.m.daocloud.io",
    "https://mirror.baidubce.com",
    "https://docker.nju.edu.cn",
    "https://ccr.ccs.tencentyun.com"
  ]
}
```
windows
1. 打开 Docker Desktop
2. 点击右上角齿轮图标 ⚙️ → 进入 Settings
3. 左侧选择 Docker Engine
4. 找到 JSON 配置内容，添加镜像加速地址：
```
{
  "registry-mirrors": [
    "https://docker.m.daocloud.io",
    "https://mirror.baidubce.com",
    "https://docker.nju.edu.cn",
    "https://ccr.ccs.tencentyun.com"
  ]
}
```

## 数据库部署
构建镜像
cd database
```
docker build -t my-postgis:latest .
```

启动容器
```
docker run -d  --name my-postgis  -e POSTGRES_PASSWORD=j3391111  -p 54321:5432  my-postgis:latest
```