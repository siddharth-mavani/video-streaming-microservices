// material-ui
import { Button, Typography, Box, Divider } from '@mui/material';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from '@mui/material';
import { useState, useEffect, useRef } from 'react';
import axios from 'axios';

// project imports
import MainCard from 'ui-component/cards/MainCard';


// ==============================|| SAMPLE PAGE ||============================== //

const Cinestream = () => {

    const [videos, setVideos] = useState([]);
    const [videoNames, setVideoNames] = useState([]);
    const [currVideoName, setCurrVideoName] = useState("");
    const [unsubscribedVideos, setUnsubscribedVideos] = useState([]);
    const [subscribedVideos, setSubscribedVideos] = useState([]);

    useEffect(() => {

        fetch('http://localhost:8080/video/all/Netflix')
            .then(response => response.json())
            .then(data => {
                setVideos(data)
                console.log(data);
                console.log("data length: " + data.length);
                let names = [];
                for (let i = 0; i < data.length; i++) {
                    // get the name of the video
                    let name = data[i].name;
                    // remove the .mp4 from the name
                    name = name.substring(0, name.length - 4);

                    names.push(name);
                }
                setVideoNames(names);
                console.log(names);

            });
    }, []);

    useEffect(() => {

        let username = localStorage.getItem("username")
        fetch('http://localhost:8082/subscription/' + username)
            .then(response => response.json())
            .then(data => {
                console.log(data['ottvideos'])
                for (let i = 0; i < videoNames.length; i++) {
                    if (data['ottvideos'].includes(videoNames[i])) {
                        setSubscribedVideos(prevArray => [...prevArray, videoNames[i]])
                    }
                    else {
                        setUnsubscribedVideos(prevArray => [...prevArray, videoNames[i]])
                    }
                }
            });
    }, [videos]);


    const handleClick = (event) => {
        setCurrVideoName(event.currentTarget.textContent + ".mp4");
        console.log(currVideoName);
    }

    const handlePurchaseClick = (purchasedVideo) => {
        // console.log(event.currentTarget.textContent);
        // add video to user's list of videos in database
        let username = localStorage.getItem("username")
        // let purchasedVideo = event.currentTarget.textContent
        axios.post(`http://localhost:8082/subscription/ott?username=${username}&videoname=${purchasedVideo}`)
            .then(response => {
                // delete video from unsubscribed list
                let videosTemp = unsubscribedVideos;
                let index = videosTemp.indexOf(purchasedVideo);
                if (index > -1) {
                    videosTemp.splice(index, 1);
                }
                setUnsubscribedVideos(videosTemp);

                // add video to subscribed list
                setSubscribedVideos(prevArray => [...prevArray, purchasedVideo]);
            })
    }

    return (

        <MainCard>
            <Typography align="center" variant="h1">
                Netflix
            </Typography>

            {currVideoName !== "" ? (
                <video key={currVideoName} width="640" height="480" controls style={{ margin: 'auto', display: 'block' }}>
                    <source src={"http://localhost:8080/video/" + currVideoName + "/Netflix"} type="video/mp4" />
                </video>
            ) : null}

            <Typography variant="h2">Subscribed Movies</Typography>

            {subscribedVideos.map((video, index) => (
                <div key={index}>
                    <Box sx={{ minWidth: '100px', width: '100%', mt: '1%' }}>
                        <Button variant="contained" onClick={handleClick}>
                            {subscribedVideos[index]}
                        </Button>
                    </Box>
                </div>
            ))}

            <br />
            <Divider />
            <br />

            <Typography variant="h2">
                Available Movies
            </Typography>
            <br />

            <TableContainer>
                <Table size="small">
                    <TableBody>
                        {unsubscribedVideos.map((video, index) => (
                            <TableRow key={index}>
                                <TableCell>{unsubscribedVideos[index]}</TableCell>
                                <TableCell align="right">
                                    <Button color="warning" variant="contained" onClick={() => handlePurchaseClick(unsubscribedVideos[index])}>
                                        Purchase
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </MainCard>
    );
};

export default Cinestream;
