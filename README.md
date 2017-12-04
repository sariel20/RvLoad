# MyHandler
Handler的运行需要底层的MessageQueue和Looper支撑

Looper  内部包含一个消息队列也就是messagequeue,所有handler发送的消息都走向消息队列
.loop方法，死循环，不断的从MessageQueue取消息，如有消息就处理消息，没有消息就阻塞

MessageQueue 消息队列，可添加信息并处理消息
Handler内部会跟Looper进行关联

handler负责发送消息,Looper负责接受Handler发送的消息， 并直接把消息给handler自己
MessageQueue就是一个存储消息的容器

Looper
Looper使普通线程变成Looper线程，也就是循环线程
Looper.prepare();//将当前线程初始化为looper线程
Looper.loop();开始循环处理消息队列
两段核心代码可以使普通线程升级为looper线程
注意：一个线程中只能存在一个looper对象
Looper.prepare();
每个线程中的looper对象其实是一个threadlocal，即本地存储对象
ThreadLocal的概念
并不是线程，作用是在每个线程中存储数据
可以在不同的线程中互不干扰的存储或提供数据
通过threadloacl可以获取每个线程中的looper

Looper.loop();
调用loop方法后线程开始工作
不断的从自己的MessageQueue中取出消息
Looper.myLooper();//得到当前线程的Looper对象
Looper.getThread();//得到Looper对象所属线程
Looper.quit();//结束循环


