with (new JavaImporter(org.roy.loadx.sample)) {
	setDefaultScenarioIterationCount(2)
	setDefaultScenarioRunIterationCount(2)
	setDefaultScenarioUserCount(2)

	getScenarioClassData(addScenario(SampleScenario))
		.put(SampleScenario.Data.URL, "http://somewhere.com")

	setJobInitializer(new SampleJobInitializer())
}