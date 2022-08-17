#!/bin/sh
#
# service start script
#
# author: luo yong
#
cd $(dirname $0)
p_dir=$( pwd -P )
echo -e "[INFO] project dir: $p_dir \n"

# config目录没用，为了nacos检查时不报错
if [ ! -d "./config" ]; then
  mkdir config
fi

show_log=$2

findJava(){
    arr1=`grep "JAVA_HOME=" /etc/profile | awk -F'=' '{print $2}'`
    if [ "$arr1" ]; then
        arr1=`echo $arr1`/bin/java
    fi

    arr2=`echo $JAVA_HOME`
    if [ "$arr2" ]; then
        arr2=`echo $arr2`/bin/java
    fi

    array=($arr1 $arr2 `which java` java)
    for i in "${!array[@]}"
    do
        java_dir=${array[$i]}
        echo -e "[INFO] Find java_dir: $java_dir \n[INFO] i: $i "
        if [ "$java_dir" ]; then
            java_ver=$($java_dir -version 2>&1 | awk -F '"' '/version/ {print $2}')
            if [[ "$java_ver" < "1.8" ]]; then
                java_dir=
            else
                echo -e "[INFO] Find java version: $java_ver \n[INFO] Find java: $java_dir "
                break
            fi
        fi
    done

    if [ ! "$java_dir" ]; then
        echo -e "\n[ERROR] Find java version: $java_ver \n[ERROR] Please install JDK 8.0+ \n"
        exit 1
    fi
}

initStartParam(){
    findJava

    jvm_opts="-server -Xmx1024m -Xms512m -Xmn200m"
    jvm_opts="$jvm_opts -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
    echo "[INFO] Using jvm args: $jvm_opts"

    boot_jar=$(ls -l | grep "jar" | awk '{print $9}')
    jar_num=$(ls -l | grep "jar" | wc -l)
    if [ ! $jar_num -eq 1 ]; then
        echo -e "\n[ERROR] jar_num: $jar_num. Make sure there is only one jar in the current directory. Please check! \n"
        exit 1
    fi

    c_path=$PWD:$PWD/config:$PWD/$boot_jar
    echo "[INFO] Using cp: $c_path"

    log_home=$PWD/logs
    if [ ! -d "$log_home" ]; then
       mkdir $log_home
    fi

#    log_cfg_file=$(find $PWD -name logback-spring.xml)
#    sed -i "s#<property name=\"LOG_HOME\" value=\".*\"\s*/>#<property name=\"LOG_HOME\" value=\"$log_home\" />#" $log_cfg_file
}

start(){
    pid=`ps -ef | grep $p_dir | grep jar | awk '{print $2}'`
	if [ ! -z "$pid" ]; then
		echo -e "[WARN] '"$p_dir"' service has been started before, pid is $pid, please close before starting \n"
	else
	    echo -e "[INFO] '"$p_dir"' service is starting \n"
	    initStartParam

        nohup $java_dir $jvm_opts -cp $c_path "org.springframework.boot.loader.PropertiesLauncher" > /dev/null 2>&1 &
        local cur_pid=$!
	    echo -e "[INFO] '"$p_dir"' service started successfully at: "`date '+%Y-%m-%d %T'`". pid is $cur_pid \n"

        local log_file=
        for var in {1..10}
        do
            if [ ! $log_file ] || [ ! -f $log_file ]; then
                sleep 2
                log_file=`ls -t $log_home/*.log | head -1`
            fi
        done

        if [ $log_file ] && [ -f $log_file ]; then
            if [ "$show_log" = "-v" ]; then
                tail -100f $log_file
            else
                sleep 5
                head -n 200 $log_file
            fi
        fi

	fi
}

stop(){
    pid=`ps -ef | grep $p_dir | grep jar | awk '{print $2}'`
    if [ ! -z "$pid" ]; then
        echo -e "[INFO] '"$p_dir"' service is stopping \n"
        kill -9 $pid
        sleep 2
        echo -e "[INFO] '"$p_dir"' service has stopped \n"
    else
        echo -e "[INFO] '"$p_dir"'service is not running \n"
    fi
}

case $1 in
  "-s" | "start")
	start;
    ;;
  "-h" | "halt" | "stop")
	stop;
    ;;
  "-r" | "restart")
    stop;
    start;
    ;;
  *)
    echo "[INFO] {[start, -s]|[stop, halt, -h]|[restart, -r]}"
    echo "[INFO] [-s|start]           Start service and show start log"
    echo "[INFO] [-s|start -v]        Start service and show detail start log"
    echo "[INFO] [-h|halt|stop]       Stop service"
    echo "[INFO] [-r|restart]         Restart service and show start log"
    echo "[INFO] [-r|restart -v]      Restart service and show detail start log"
    ;;
esac
exit 0
