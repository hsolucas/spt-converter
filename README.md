# spt-converter

Objetivo de unificar as informações de todas as linhas de ônibus da cidade de São Paulo, possibilitando visualizar por linha o traçado do itinerário, os dados de passageiros pagantes, ano de criação e desativação da linha, entre outros.

Como fonte das informações, foram utilizados:
- arquivos de GTFS da SPTRANS (https://www.sptrans.com.br/desenvolvedores/)
- dados de bilhetagem do acesso à informação da SPTRANS (https://www.prefeitura.sp.gov.br/cidade/secretarias/mobilidade/institucional/sptrans/acesso_a_informacao/index.php?p=152416)
- banco de dados de linhas de ônibus gerado pelo Centro de Estudos da Metrópole da USP (https://centrodametropole.fflch.usp.br/pt-br/node/8364) - Este com os últimos dados de 2017

É gerado um arquivo geojson que, entre outras alternativas, pode ser visualizado através do aplicativo QGIS, online pelo site https://geojson.io ou https://mapshaper.org ou convertido em um banco de dados PostGIS.
