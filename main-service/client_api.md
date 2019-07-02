* 下面所有请求的 ajax=true 参数应该放到请求头里面，下面这么写只是为了方便测试

http://localhost:8080/energy/login?ajax=true&loginId=admin&loginPwd=111111
http://localhost:8080/energy/basic/station/actualdataAjax?ajax=true
http://localhost:8080/energy/basic/station/historydataAjax?ajax=true&tTime=2019-05-17 13:00
http://localhost:8080/energy/infoOverview?ajax=true

http://localhost:8080/energy/report/day/list?ajax=true&searchDateTime=2019-05-16
http://localhost:8080/energy/report/month/list?ajax=true&searchDateTime=2019-05-17
http://localhost:8080/energy/report/quarter/list?ajax=true&quarter=2
http://localhost:8080/energy/report/year/list?ajax=true&year=2019
http://localhost:8080/energy/report/econ/list?ajax=true&beginTime=2019-04-15&endTime=2019-05-16

http://localhost:8080/energy/basic/analysis/water/list?ajax=true&beginTime=2019-05-10&endTime=2019-05-11&stationName=
http://localhost:8080/energy/basic/analysis/electricity/list?ajax=true&beginTime=2019-04-15&endTime=2019-05-16&stationName=
http://localhost:8080/energy/basic/analysis/heat/list?ajax=true&beginTime=2019-04-15&endTime=2019-05-16&stationName=

http://localhost:8080/energy/basic/station/list?ajax=true

http://localhost:8080/energy/weather/pandect?ajax=true
http://localhost:8080/energy/weather/weatherDetail?ajax=true
http://localhost:8080/energy/weather/situation/list?ajax=true&beginTime=2019-05-10&endTime=2019-05-11

http://localhost:8080/energy/predict/history/history_data2?ajax=true&beginTime=2019-05-30
http://localhost:8080/energy/predict/history/history_data3?ajax=true&beginTime=2019-05-30

http://localhost:8080/energy/tecolcurve/runningConclusion?ajax=true