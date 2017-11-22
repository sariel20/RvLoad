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

Handler
通知MessageQueue要执行一个任务（sendMessage），并在loop自己的时候执行该任务（handlerMessage），整个过程是异步的
handler创建时会关联一个looper，默认的构造方法将关联当前线程的looper，不过这也是可以set的
一个线程中可以有多个handler，但只能有一个looper
 post(Runnable), postAtTime(Runnable, long), postDelayed(Runnable, long), sendEmptyMessage(int), sendMessage(Message), sendMessageAtTime(Message, long)和 sendMessageDelayed(Message, long)
使用以上方法可以向MessageQueue发送消息
post发出的message,其callback为runnable对象
消息处理
通过核心方法dispatchMessage(Message msg)与handlerMessage(Message msg)完成
Handler可以再任意线程发送消息，这些消息会被添加到关联的messagequeue上
这就解决了android不能再非UI线程中更新UI的问题
android 主线程也是一个looper线程
利用handler的一个solution就是在activity中创建handler并将其引用传递给work thread,
work thread处理完成之后使用handler发送消息通知activity更新ui
在整个消息处理机制中，message又叫task，封装了任务携带的信息和处理该任务的handler
注意几点
1.通过Message.obtain()来从消息池中获取空消息对象，节省资源
2.如果你的message只传递int类型的数据，优先使用message.arg1和arg2，这样更加省内存
3.擅长用message.what来标示信息，以便用不同的方式处理handler
HandlerThread 解决多线程并发问题
继承自Thread，线程运行后同时创建含有消息队列的looper，并对外提供自己的Looper对象get方法
好处：
开发中如果多次使用new Thread这种方法开启子线程会创建多个匿名线程，导致程序变慢，使用HandlerThread自带的looper使他可以通过消息来多次重复使用当前线程，节省开支
对于非UI线程又想使用消息机制，用HandlerThread内部的Looper是最合适的，不会干扰或阻塞UI线程
步骤：
handlerThread = new HandlerThread("thread");
handlerThread.start();
workHandler = new WorkHandler(handlerThread.getLooper());
如果一个线程要处理消息，那么它必须拥有自己的looper
