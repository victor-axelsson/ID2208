<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>

	<xsl:variable name="cv" select="document('cv.xml')"/>
	<xsl:variable name="employmentRecord" select="document('employmentRecord.xml')"/>
	<xsl:variable name="transcript" select="document('transcript.xml')"/>
	<xsl:variable name="company" select="document('company.xml')"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<ns2:profile xmlns:ns2="http://www.kth.se/ns/jobservicecompany">
			<xsl:copy-of select="$cv/*/firstName"/>
			<xsl:copy-of select="$cv/*/lastName"/>
			<position>
				<companyName>
					<xsl:value-of select="$employmentRecord/*/position/company"/>
				</companyName>
				<xsl:copy-of select="$employmentRecord/*/position/role"/>
				<xsl:copy-of select="$employmentRecord/*/position/responsibilities"/>
				<xsl:copy-of select="$employmentRecord/*/position/startDate"/>
				<xsl:copy-of select="$employmentRecord/*/position/finishDate"/>
				<xsl:copy-of select="$company/*/office"/>
			</position>
			<university>
				<xsl:copy-of select="$transcript/*/university/degree"/>
				<GPA>
					<xsl:value-of select="sum($transcript/*/university/course/grade) div count($transcript/*/university/course/grade)" />
				</GPA>
				<xsl:copy-of select="$transcript/*/university/course"/>
				<xsl:copy-of select="$transcript/*/university/startDate"/>
				<xsl:copy-of select="$transcript/*/university/finishDate"/>
			</university>
			<project>
				<xsl:copy-of select="$cv/*/project/startDate"/>
				<xsl:copy-of select="$cv/*/project/finishDate"/>
				<xsl:copy-of select="$cv/*/project/name"/>
			</project>
		</ns2:profile>
	</xsl:template>
</xsl:stylesheet>