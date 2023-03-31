##Docker 命令
###docker相关的命令
1. 重启:  
`systemctl restart docker`
2. 查看docker信息:  
`docker info`
3. 启动docker服务:  
`systemctl start docker`
4. 停止docker服务:  
`systemctl stop docker`
5. 重启docker服务:  
`systemctl restart docker`
6. 查看docker服务状态:  
`systemctl status docker`
7. 设置开机启动docker服务:  
`systemctl enable docker`
###镜像相关的命令
1. 查看镜像: 查看本地所有的镜像  
`docker images`
2. 搜索镜像:从网络中查找需要的镜像  
`docker search 镜像名称`
3. 拉取镜像:从Docker仓库下载镜像到本地，镜像名称格式为 名称:版本号  
`docker pull 镜像名称:版本号  /  docker pull 镜像名称(默认的版本号是latest)`  
`docker pull mysql:5.7  /  docker pull mysql`
4. 删除镜像: 删除本地镜像（删除指定本地镜像）  
`docker rmi 镜像id`
###容器相关命令
1. 查看容器  
`docker ps` 查看正在运行的容器  
`docker ps -a` 查看所有容器,包括未运行的容器
2. 创建并启动容器  
`docker run -id --name=自定义容器名称 -p 映射端口:镜像端口 镜像名称`  
run: 启动  
-id/-di:  创建容器  
--name:  为创建的容器起别名  
-p: 端口映射  
3. 停止容器
`docker stop 容器名称`
4. 启动容器
`docker start 容器名称`
5. 删除容器
`docker rm 容器名称`
###查看日志的方式
1. 时时查看日志,用的指令: 
`docker logs -f -t -n=5 [服务名称或者容器名称]`
2. 查看某一段的日志,用的指令: 
`docker logs --tail=500 [服务名称或容器id]`
3. 查看容器是否启动成功用的指令: 
`docker ps -a`
4. 查看某个日期至今的所有日志, 并持续打印,使用的指令: 
`docker logs -f --since "时间如:2022-06-22" [容器id或服务名称]`