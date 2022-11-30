Git:

# GitHub Start
#20.205.243.166 github.com
140.82.113.4 github.com
199.232.69.194 github.global.ssl.fastly.net
185.199.108.153 assets-cdn.github.com
185.199.109.153 assets-cdn.github.com
185.199.110.153 assets-cdn.github.com
185.199.111.153 assets-cdn.github.com
# GitHub End

如果你所在地区不翻墙的情况下无法打开github.com网站，那么通过修改电脑本地hosts文件就可以正常打开github.com。
其中http://assets-cdn.github.com是固定的，
http://github.com和http://github.global.ssl.fastly.net会随时改变，如果不行了的话，
在以下两个网站中查看现在的ip地址，并进行修改即可
http://ip.tool.chinaz.com/github.com
http://ip.tool.chinaz.com/github.global.ssl.fastly.net
甚至直接用查到的http://github.com的ip访问也行。
更新dns缓存
一般修改过就会生效，不行的话试试刷新缓存
Windows：
ipconfig /flushdns

git拉取跟提交代码时仍然出现超时的问题，是因为使用了https的链接，这里改成使用SSH的方式会流畅许多。
1. 检查是否存在sshkey
在C:\Users\用户名\.ssh文件夹下可以看到当前电脑的sshkey。如果有 id_rsa 和 id_rsa.pub 两个文件，就说明已经存在了。

2. 生成密钥对
在 cmd控制台 上使用如下命令来生成 sshkey：
ssh-keygen -t rsa -C "xxxxx@xxxxx.com"
注意：这里的 xxxxx@xxxxx.com 只是生成的 sshkey 的名称，并不约束或要求具体命名为某个邮箱。
网上的大部分教程均讲解的使用邮箱生成，其一开始的初衷仅仅是为了便于辨识所以使用了邮箱。

输入上面的指令并回车后会弹出一些提示：
第一次提示保存路径，一般不需要修改，直接回车就行
第二次提示输入密码短语（等于是sshkey的密码），可以有也可以没有（建议还是设置一下，设置后在使用ssh拉取代码时需要这个密码验证），注意输入密码的时候是不会回显的
第三次是重复输入密码
完成三次操作后就生成完毕了，这时候再到.ssh的文件下面去看，能看到你的id_rsa 和 id_rsa.pub 两个文件，就说明你已经生成成功了。
注意这个 id_rsa.pub 是公钥，将来要放到GitHub上的，另一个id_rsa是私钥，等于是你的密码，保存好，不要随便发给别人。

3. 把公钥放到GitHub上
点击头像 -> settings -> 点击左边 SSH and GPGkeys -> 再点击右边 New SSH key

4.测试验证
然后到本机找个文件夹打开 Git Bash，输入 ssh -T git@github.com
如果，看到如下信息提示
Hi xxx! You've successfully authenticated, but GitHub does not # provide shell access.
恭喜，配置成功！

git取消代理命令
打开 Git Bash
git config --global --unset http.proxy
git config --global --unset https.proxy

