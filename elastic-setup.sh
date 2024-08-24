export ELASTIC_PASSWORD="elastic"
export KIBANA_PASSWORD="kibana"




docker run -p 192.168.1.12:9200:9200 -d --name elasticsearch --network elastic-net \
 -e ELASTIC_PASSWORD=$ELASTIC_PASSWORD \
 -e "discovery.type=single-node" \
 -e "xpack.security.http.ssl.enabled=false" \
 -e "xpack.license.self_generated.type=trial" \
 docker.elastic.co/elasticsearch/elasticsearch:8.15.0

sleep 10s

# configure the Kibana password in the ES container
curl -u elastic:$ELASTIC_PASSWORD \
 -X POST \
 http://192.168.1.12:9200/_security/user/kibana_system/_password \
 -d '{"password":"'"$KIBANA_PASSWORD"'"}' \
 -H 'Content-Type: application/json'


 docker run -p 192.168.1.12:5601:5601 -d --name kibana --network elastic-net \
 -e ELASTICSEARCH_URL=http://elasticsearch:9200 \
 -e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
 -e ELASTICSEARCH_USERNAME=kibana_system \
 -e ELASTICSEARCH_PASSWORD=$KIBANA_PASSWORD \
 -e ENTERPRISESEARCH_HOST=http://localhost:3002 \
 -e "xpack.security.enabled=false" \
 -e "xpack.license.self_generated.type=trial" \
 docker.elastic.co/kibana/kibana:8.15.0
