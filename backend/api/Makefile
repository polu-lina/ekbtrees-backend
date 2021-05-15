PROD_USER=mnagovitsin
PROD_HOST=68.183.12.193

build-image:
	@cd scripts && ./buildImage.sh $(env)

publish-image:
	@docker push donkeyhott/ectm-api:latest

update-prod:
	@cd scripts && ./updateProd.sh $(PROD_USER) $(PROD_HOST)