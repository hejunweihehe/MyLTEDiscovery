# LTEDiscovery

功能：
1.列出当前网络连接的状态（包括断开或者是正在连接）等常发生改变的信息。除了网络连接状态之外，还可以显示出当前连接的移动网络名称（LTE、GSM、HSPA等）。网络信号强度dbm、信号等级（总共4级）。基站位置LAC、CID等。

信号等级在SignalStrength的getLevel方法获取，该方法获取到的是当前正在连接的移动网络的信号等级。指定的移动网络有它们自己的获取信号等级的方法，比如说lte的是getLteLevel，GSM的是getGsmLevel，当然只有当前连接的移动网络信号等级不为零，其他的全为零。

2.显示手机信息，包括网络运营商名称（中国联通、中国移动）、国家代码（中国是cn）、MNC+MCC、手机型号、手机类型（GSM、CDMA、SIP等）、是否漫游等。

下面是获取上述信息的方法
网络运营商名称：TelephonyManager的getNetworkOperatorName()方法
国家代码：TelephonyManager的TelephonyManager()方法。
MNC+MCC:TelephonyManager的getSimOperator()方法。
手机型号：Build.MANUFACTURER+Build.BOARD+Build.BRAND
手机类型：TelephonyManager的getPhoneType()方法。
是否漫游可以使用TelephonyManager的isNetworkRoaming方法。

3.当前连接网络的详细信号，这里需要在信号改变的时候立即响应。这里是第一个麻烦的地方，因为4G的信号和2G、3G的显示格式是不一样的，而由于在我们移动的时候，或者是在网络不好的地方，会很容易发生网络切换，比如从4G切换到3G，然后过一会又切换回来，这时候就不能仅仅只是监听其中一个的信号了。在相关类型的app中发现了下面的几个方法可供参考：

a.一次只显示一种网络信号，系统保留切换不同类型信号的接口，比如说阿达4G路测和Signalsitemap，点击进入基站信息图表，他们通过四	个按钮，可以切换到为2G/3G，4G的三个信号（RSRP,RSRQ,SINR）。

b.把4G的信号和2G/3G的信号分别都显示，哪边有信号就显示哪边的。LTEDiscovery和网优百宝箱都是这个样子的。值得注意的是，它们都选择把2G和3G放在一起，因为两者的信号格式相同。

下面列出需要显示的信号强度参数，所有的数据均来自于SignalStrength类。
4G信号
RSRP,getLteRsrp(),单位dB
RSRQ,getLteRsrq(),单位dBm
SNR,getLteRssnr(),单位dB

2G/3G信号
lac
cid
psc

4.周围的基站信息
这里可以参考LTEDiscovery、Netmonitor、NetMonster，后面两个会把所有扫描到的基站信息以保存下来。
获取周围基站信息最好使用getNeighboringCellInfo。

基站有两个获取的方式，都是TelephonyManager的方法，分别是getAllCellInfo和getNeighboringCellInfo。
根据android API介绍，只有在getAllCellInfo方法获取不到数据时，再去调用getNeighboringCellInfo。当然我们也可以两个方法都调用，但是我在不同的机子测试的时候发现，这两个方法一般只有其中一个有数据，另一个则为空。所以暂时可以按照android系统的思路，先查看getAllCellInfo获取数据是否为空，为空则去getNeighboringCellInfo中获取，然后在页面上做一个TextView来提示用户，数据是从getAllCellInfo来的那么就显示“Serving”，数据是从getNeighboringCellInfo来的，就显示“Neighbor”

保存的信息如下：
在NeighboringCellInfo中所有能够获取到的公共字段属性。还有发现的日期。

所有的数据存到数据库里边，保留一个清除记录的按钮。
