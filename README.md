* Proceso actual desde Pipeline
* Comandos para logearte en OpenShift

`oc login -u system:admin`

`oc login -u developer -p developer`

`oc login --token=4gfsdfgsdf34rwsedfsdxf https://192.168.99.432:8443/ --insecure-skip-tls-verify=true`

* Accediendo al namespace donde se alojará el servicio

`oc project project-openshift` 

* Limpiando archivos que pudieran inteferir en el deploy

`oc delete bc/ms-openshiftdemo --ignore-not-found=true -n project-openshift`

`oc delete is/ms-openshiftdemo --ignore-not-found=true -n project-openshift`

`oc delete dc/ms-openshiftdemo --ignore-not-found=true -n project-openshift`

`oc delete all -l app=ms-openshiftdemo --ignore-not-found=true -n project-openshift`

`oc delete all --selector app=ms-openshiftdemo -n project-openshift`

* Realizando la construcción de la aplicación

`oc new-build --binary=true --strategy=source --name='ms-openshiftdemo' --image-stream=openshift/openjdk-11-rhel7:1.1 -n project-openshift`

* Comando para crear una aplicación a partir de la construcción realizada.

`oc new-app project-openshift/ms-openshiftdemo:latest --name=ms-openshiftdemo --allow-missing-imagestream-tags=true -e JAVA_OPTS="-Dapp.properties=/deployments/config/application.properties  -Djavax.net.ssl.trustStore=/deployments/config/certificados-ms-openshiftdemo.jks  -Djavax.net.ssl.trustStorePassword=changeit" --as-deployment-config -n project-openshift `

* Comando para aumentar los recursos del deployment config

`oc set resources dc ms-openshiftdemo --limits=memory=400Mi,cpu=200m --requests=memory=300Mi,cpu=100m -n project-openshift`

* Comando para eliminar los triggers que pueda tener el deployment config

`oc set triggers dc/ms-openshiftdemo --remove-all -n project-openshift`

* Comando para eliminar el configmap ms-openshiftdemo-config si existe

`oc delete configmap ms-openshiftdemo-config --ignore-not-found=true -n project-openshift`

* Comando para crear el configmap ms-openshiftdemo-config

`oc create configmap ms-openshiftdemo-config -n project-openshift`
* Comando para asignar un volumen al deployment config de tipo configmap que almacenará las propiedades del application.properties

`oc set volume dc/ms-openshiftdemo --add --type=configmap --name=ms-openshiftdemo-config --mount-path=/deployments/config/application.properties --sub-path=application.properties --configmap-name=ms-openshiftdemo-config -n project-openshift`

* Comando para asignar un volumen al deployment config de tipo secret que almacenará el api key

`oc set volume dc/ms-openshiftdemo --add --type=secret --name=apikey-ms-openshiftdemo --mount-path=/deployments/config/apikey.txt --sub-path=apikey.txt --secret-name=apikey-ms-openshiftdemo --read-only=true --overwrite  -n project-openshift`

* Comando para asignar un volumen al deployment config de tipo secret que almacenará el jks donde se encuentran los certificados

`oc set volume dc/ms-openshiftdemo --add --type=secret --name=certificate-ms-openshiftdemo --mount-path=/deployments/config/certificados-ms-openshiftdemo.jks --sub-path=certificados-ms-openshiftdemo.jks --secret-name=certificate-ms-openshiftdemo --read-only=true --overwrite  -n project-openshift`

* Comando para eliminar el service ms-openshiftdemo ignorando si no lo encuentra

`oc delete svc/ms-openshiftdemo --ignore-not-found=true -n project-openshift`

* Comando para exponer el puerto 8080

`oc expose dc ms-openshiftdemo --port 8080 -n project-openshift`

* Comando para crear una ruta segura con terminación EDGE https://stackoverflow.com/questions/64812296/openshift-origin-v3-edge-passthrough-and-encrypt-termination

`oc create route edge --service=ms-openshiftdemo -n project-openshift`


* Comando para volver a realizar la construcción del servicio a partir del jar

`oc start-build ms-openshiftdemo --from-file=./target/openshiftdemo-0.0.1-SNAPSHOT.jar --wait=true -n project-openshift`


* Comando para eliminar el configmap ms-openshiftdemo-config si existe

`oc delete cm ms-openshiftdemo-config --ignore-not-found=true -n project-openshift`

* Comando para crear el configmap ms-openshiftdemo-config con el application.properties

`oc create cm ms-openshiftdemo-config --from-file=./src/main/resources/application.properties -n project-openshift`

* Comando para eliminar el secret que almacena el api key

`oc delete secret apikey-ms-openshiftdemo --ignore-not-found=true -n project-openshift`

* Comando para eliminar el secret que almacena la contraseña del jks que almacena los certificados

`oc delete secret certificate-ms-openshiftdemo --ignore-not-found=true -n project-openshift`

* Comando para crear el secret que almacena el api key

`oc create secret generic apikey-ms-openshiftdemo --from-file=./src/main/resources/apikey.txt -n project-openshift`

* Comando para crear el secret que almacena la contraseña del jks que almacena los certificados

`oc create secret generic certificate-ms-openshiftdemo --from-literal=certificate-ms-openshiftdemo=changeit --from-file=./src/main/resources/certificados-ms-openshiftdemo.jks -n project-openshift`

* Comando para asignar la imagen contruida al deployment config

`oc set image dc/ms-openshiftdemo ms-openshiftdemo=project-openshift/ms-openshiftdemo:0.0.1-3489sd --source=imagestreamtag -n project-openshift`

* Comando para ejecutar el deploy del servicio de manera manual (equivalente a: oc new-app...)

`oc rollout latest dc/ms-openshiftdemo -n project-openshift`


* Proceso mejorado

`oc project project-openshift`

`oc new app openshift/openjdk-11-rhel7:1.1~https://cohvsappbit01.corp.popular.local:8443/scm/bms/ms_contextualmessaging.git --name ms_contextualmessaging -e JAVA_OPTS='-Dapp.properties=/deployments/config/application.properties  -Djavax.net.ssl.trustStore=/deployments/config/falcon.jks  -Djavax.net.ssl.trustStorePassword=changeit'`

`oc expose svc ms_contextualmessaging --port 8080`


* Nuevos lineamientos de pipeline
`oc new-project adiellara-prod`
`oc project adiellara-prod`
`oc create secret generic apikey-ms-openshiftdemo2 --from-file=src/main/resources/apikey.txt`

* Ejecutar este comando solo si no existe el secreto que almacena el jks.
`oc create secret generic certificate-ms-openshiftdemo2 --from-literal=certificate-ms-openshiftdemo=changeit --from-file=src/main/resources/certificados-ms-openshiftdemo.jks`
* Se puede ejecutar este comando
`oc apply -f configMap.yaml`
* O
`oc create cm ms-openshiftdemo-config --from-file=./src/main/resources/application.properties`

* Aplicar el DeploymentConfig
`oc apply -f deploymentConfig.yaml`
`oc expose dc/ms-openshiftdemo2`
`oc create route edge --service=ms-openshiftdemo2`