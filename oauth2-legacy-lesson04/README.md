# oauth2-legacy-lesson02

curl client:secret@localhost:8083/oauth/token -dgrant_type=client_credentials -dscope=read
curl client:secret@localhost:8083/oauth/token -dgrant_type=password -dusername=user -dpassword=password -dscope=read

http://localhost:8083/oauth/authorize?response_type=code&client_id=client&scope=read&redirect_uri=http://localhost:8082/lsso-client/login/oauth2/code/custom

