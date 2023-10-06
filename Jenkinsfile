String sectionHeaderStyle = '''
    color: white;
    background: gray;
    font-family: Roboto, sans-serif !important;
    padding: 3px;
    text-align: center;
'''

String separatorStyle = '''
    border: 0;
    border-bottom: 1px #ccc;
    background: #999;
'''

properties([
    disableConcurrentBuilds(),
    [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '10']],
    parameters([                  
        [
            $class: 'ParameterSeparatorDefinition',
            name: 'BUILDER_HEADER',
            sectionHeader: 'Docker Builder Parameters',
            separatorStyle: separatorStyle,
            sectionHeaderStyle: sectionHeaderStyle
        ],
        booleanParam(defaultValue: false, description: 'Update builder', name: 'makeBuilder'),
        string(
            name: 'builderVersion',
            defaultValue: 'x.x.x',
            description: 'Type the version of the docker container to be created.'
        ),        
        [
            $class: 'ParameterSeparatorDefinition',
            name: 'BUILD_HEADER',
            sectionHeader: 'Build Parameters',
            separatorStyle: separatorStyle,
            sectionHeaderStyle: sectionHeaderStyle
        ],
        booleanParam(defaultValue: true, description: 'Build', name: 'build'),
        string(
            name: 'selectedBuilderVersion',
            defaultValue: '1.0.0',
            description: 'Type the version of the docker container to be used in the build stage.'
        ),
        [
            $class: 'ParameterSeparatorDefinition',
            name: 'DEPLOY_HEADER',
            sectionHeader: 'Deploy Parameters',
            separatorStyle: separatorStyle,
            sectionHeaderStyle: sectionHeaderStyle
        ],
        booleanParam(defaultValue: false, description: 'Deploy', name: 'deploy'),
        choice(choices: 'prd\nhml', description: 'Select the deploy environment', name: 'ambiente')
    ])
])

@Library("prodaub") _
node{

    def pipeline    = new com.prodaub.Default()
    
    def projectName = "dec"
    def buildername = "dec-server"
    def dockerService = "dec-server"
    def dockerRegistryCredentials = "dockerRegistryCredentials"
    def bitbucketCredentials = "jenkins"
    def builderImageVersion  = "${params.selectedBuilderVersion}"
    def emails = "adalberto.maciel@ibrowse-sds.com.br,thiago.castro@ibrowse-sds.com.br,ana.silva@ibrowse-sds.com.br"
    def m2path = "/var/jenkins_home/.m2"

    try{
        pipeline.emailNotifcation(emails,"Pipeline iniciado.")
        pipeline.cleanDir()
        pipeline.cloneRepository()
        pipeline.updateBuilderImage(buildername, //Builder image name
                                dockerRegistryCredentials //Docker Registry Credentials id
                                )
    
        pipeline.pullBuilder(buildername,  // Builder image name
                            builderImageVersion, // Builder image version
                            dockerRegistryCredentials // Docker Registry Credentials id
                            )

        def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    
        pipeline.buildMaven(
            buildername, // Builder image name
            builderImageVersion, // Builder image version
            "mvn -s settings.xml -Dmaven.repo.local=/m/repository clean deploy -DbuildNumber=hash${shortCommit} -Pprod -DprofileIdEnabled=true -DskipTests", // Effective build command
            "mvn -s settings.xml -Dmaven.repo.local=/m/repository clean install package -U -DbuildNumber=hash${shortCommit} -Pprod -DprofileIdEnabled=true -DskipTests", // Test build command
            m2path, // jenkins .m2 directory path
            dockerService, // project name used in rocket messaging
            emails,
        )    
    
        pipeline.deploy(projectName,dockerService,emails)
        pipeline.emailNotifcation(emails,"Pipeline finalizado")
    }catch(e){
        pipeline.emailNotifcation(emails,"Erro no pipeline: ${e}")
    }

}
