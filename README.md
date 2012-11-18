An ASM based class file analyzer.  Built to assist in Gradle dependency analysis.

Requires a deploy.gradle in the root directory that contains,
typically, Nexus or Artifactory deployment credentials.  The file
is of the form:

ext.deploy = [ 
   user: "theuser",
   password: "thepassword"
] 
