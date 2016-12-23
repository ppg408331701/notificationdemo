# notificationdemo
点击notification时的跳到栈顶


之前有一个需求,当用户点击APP常驻在通知栏的notification时,有两种情况要处理:

1.用户之前打开过APP,APP的栈中存在有几个activity时,用户点击notification跳转到栈顶的activity. 比如,主页——个人中心——修改密码. 
这时用户按home键去做其他的事情,当用户点击通知栏的通知时跳转到栈顶的修改密码

2.用户APP的栈内没有任何activity,当用户点击通知栏的通知时,跳转到设置好的主页.(通知栏的通知是常驻型的,即使APP被系统或安全软件杀死也会保留在通知栏)
