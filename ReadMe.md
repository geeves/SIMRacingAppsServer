# About

This is a Fork of the [SIMRacingAppsServer](https://github.com/SIMRacingApps/SIMRacingAppsServer) 
and all because I did not want to work in the AngularJS framework which 
I dragged out... in to a dark alley. And shot. Execution-style... back in 2015.

The main goal of this was to cure COVID boredom at home when not working or on [iRacing](https://iracing.com) and trying to be productive.

When starting to create an app based on SRA's API, and since I don't do much dev on Windows I needed a way to 
communicate the SRA server.  That immediately lead to issues with CORS and how to create a maze of workflow 
including MacOS, Linux and maybe iOS.

When I dug into the problem, I came across this [CORS issue - https://github.com/SIMRacingApps/SIMRacingApps/issues/205](https://github.com/SIMRacingApps/SIMRacingApps/issues/205), that was discussed, but nothing really done about it.

The other issue is that SRA's server is greedy and immediately goes after ports 
80 and 8080 which are the most commonly used - but I understand the reasoning due 
to its simplicity.  It's now randomly 43087, however the end goal is to be 
able to assign it to your the port of your choosing.

Finally, to get around all of this for the [project](https://github.com/geeves/project-osbourne) I want to be working on, 
needed a proxy to make things more simple - so nginx, on Windows.  Awesome.

If you do grab this repo, add simracingapps.io to your hosts file (public ip) 
and install and update nginx.conf.

More on this here: https://github.com/geeves/project-osbourne
