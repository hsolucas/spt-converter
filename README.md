# spt-converter

Objetivo de unificar as informações de todas as linhas de ônibus da cidade de São Paulo, possibilitando visualizar por linha o traçado do itinerário, os dados de passageiros pagantes, ano de criação e desativação da linha, entre outros.

Como fonte das informações, foram utilizados:
- arquivos de GTFS da SPTRANS (https://www.sptrans.com.br/desenvolvedores/)
- dados de bilhetagem do acesso à informação da SPTRANS (https://www.prefeitura.sp.gov.br/cidade/secretarias/mobilidade/institucional/sptrans/acesso_a_informacao/index.php?p=152416)
- banco de dados de linhas de ônibus gerado pelo Centro de Estudos da Metrópole da USP (https://centrodametropole.fflch.usp.br/pt-br/node/8364) - Este com os últimos dados de 2017

É gerado um arquivo geojson que, entre outras alternativas, pode ser visualizado através do aplicativo QGIS, online pelo site https://geojson.io ou https://mapshaper.org ou convertido em um banco de dados PostGIS.

## uso

- Descompactar o arquivo base de dados do CEM em files/input
- Baixar a planilha com o total mensal de passageiros transportados no site de acesso à informação da SPTrans e converter a parte Geral_Consolidado para CSV separado por vírgulas e salvar em files/input/sptrans
- Baixar os arquivos de GTFS do site de desenvolvedor da SPTrans e salvar em files/input/sptrans
- Instalar o Java 11 ou superior
- Executar comando javac SptConverter.java
- Executar comando java SptConverter
- Após o processamento o novo arquivo geojson estará disponível em files/output
