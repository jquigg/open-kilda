# 'make' will build the latest and try to run it.
default: update-props build-latest run-dev

UPDATE_PROPS := confd -onetime -confdir ./confd/ -backend file -file ./confd/vars/main.yaml -file ./confd/vars/docker-compose.yaml -sync-only

generated.mk update-props:
	$(UPDATE_PROPS)

update-props-dryrun:
	$(UPDATE_PROPS) -noop

.PHONY: default update-props update-props-dryrun

include generated.mk
