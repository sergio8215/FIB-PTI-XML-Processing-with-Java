<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/carrental">
		<html>
			<head>
				<title>Car Rentals</title>
			</head>
			<body>
				<h1>Car Rental List</h1>
				<xsl:for-each select="rental">
					<h4><xsl:value-of select="make"/> - <xsl:value-of select="model"/></h4>
					<table border="0">
						<tr><td>Num days:</td><td><xsl:value-of select="numdays"/></td></tr>
						<tr><td>Num units:</td><td><xsl:value-of select="nounits"/></td></tr>
						<tr><td>Discount:</td><td><xsl:value-of select="discount"/></td></tr>
					</table>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>