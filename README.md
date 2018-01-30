# PassengersMonitor
Esse projeto objetiva visualizar o deslocamento das pesssoas na cidade através dos transportes públicos

O projeto é dividido em dois módulos:

**SignalSender**

Esse módulo irá rodar em um dispositivo android e foi construído utilizando java. O módulo irá obter a localização gps do usuário e enviá-la para um servidor juntamente com outras informações.

Requirements:

1. Android SDK API level 26
2. Java 8
3. Gradle

**MonitorServer**

Esse módulo foi construído utilizando python com django. O módulo irá prover uma API REST para obter informações de localização oriundas de dispositivos android.

Requirements:

1. python 2.7
2. django 1.11
3. djangorestframework 3.7.7​
26
Para executar 
