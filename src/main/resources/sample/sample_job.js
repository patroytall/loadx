with (new JavaImporter(org.roy.loadx.sample)) {
	setDefaultScenarioIterationCount(2)
	setDefaultScenarioRunIterationCount(2)
	setDefaultScenarioUserCount(2)

	setJobInitializer(new SampleJobInitializer())
	
	setScenario(SampleScenario)
	getScenarioClassData(SampleScenario).put(SampleScenario.Data.URL, "http://somewhere.com")
}