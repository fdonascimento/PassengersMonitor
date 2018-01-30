# PassengersMonitor
Esse projeto objetiva visualizar o deslocamento das pesssoas na cidade através dos transportes públicos

O projeto é dividido em dois módulos:

**SignalSender**

Esse módulo irá rodar em um dispositivo android e foi construído utilizando java. O módulo irá obter a localização gps do usuário e enviá-la para um servidor juntamente com outras informações.

Requirements:

1. Android SDK 
2. Java 8
3. Gradle
4. API level 26

A IDE utilizada foi o AndroidStudio.

Configuração do ambiente e execução do módulo:
1. Instalar o Java8
2. Instalar Android SDK
3. Instalar o AndroidStudio.
4. Instalar a api level e as imagens através do AndroidStudio.
5. Procurar na classe MainActivity por .baseUrl("").  Alterar o IP para o IP configurado no módulo MonitorServer.
6. Instalar a apk no android.

**MonitorServer**

Esse módulo foi construído utilizando python com django. O módulo irá prover uma API REST para obter informações de localização oriundas de dispositivos android.

Requirements:
 
1. python 2.7
2. django 1.11
3. djangorestframework 3.7.7

A IDE utilizada foi o PyCharm.

Configuração do ambiente e execução do módulo:

1. Instalar o python 2.7
2. Instalar o virtualenv através do comando: **pip install virtualenv**. O pip vem junto com o python.
3. Exectuar o comando **virtualenv ENV** dentro do diretório GpsReceiver. Esse comando irá criar o ambiente virtual no diretório ENV.
4. Entrar no diretório ENV e executar o comando **source bin/activate**. Para mais detalhes sobre o virtualenv, acessar o link: https://virtualenv.pypa.io/en/stable/
5. Instalar o django através do comando **pip install Django==1.11**
6. Instalar o djangorestframework através do comando **pip install djangorestframework**
7. No diretório GpsReceiver executar o comando **python manage.py migrate**
8. No arquivo settings.py do diretório GpsReceiver/GpsReceiver, alterar o campo ALLOWED_HOSTS e colocar o IP da máquina que irá rodar o server.
9. Executar o comando **python manage.py runserver ip:porta**

Ao final dessa sequência de passos o serviço estará rodando e poderá ser acessado através da url http://ip:porta/locations/locations
