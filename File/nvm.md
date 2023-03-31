##nvm的安装
<https://github.com/coreybutler/nvm-windows/releases>
`下载nvm-setup.zip`
##nvm的使用
1. nvm off                     // 禁用node.js版本管理(不卸载任何东西)
2. nvm on                      // 启用node.js版本管理
3. nvm install <version>       // 安装node.js的命名 version是版本号 例如：nvm install 8.12.0
4. nvm uninstall <version>     // 卸载node.js是的命令，卸载指定版本的nodejs，当安装失败时卸载使用
5. nvm ls                      // 显示所有安装的node.js版本
6. nvm list available          // 显示可以安装的所有node.js的版本
7. nvm use <version>           // 切换到使用指定的nodejs版本
8. nvm v                       // 显示nvm版本
9. nvm install stable          // 安装最新稳定版