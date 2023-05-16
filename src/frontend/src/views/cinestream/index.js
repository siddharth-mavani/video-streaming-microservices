// material-ui
import { Button, Typography, Box, Dialog, DialogTitle, DialogContent, DialogActions, DialogContentText, Grid, Autocomplete, TextField } from '@mui/material';
import { useState, useEffect } from 'react';
import axios from 'axios';

// project imports
import MainCard from 'ui-component/cards/MainCard';


// ==============================|| SAMPLE PAGE ||============================== //

const Cinestream = () => {

    const [videos, setVideos] = useState([]);
    const [open, setOpen] = useState(false);
    const [videoNames, setVideoNames] = useState([]);
    const [currVideoName, setCurrVideoName] = useState("");
    const [subscriptionType, setSubscriptionType] = useState(localStorage.getItem("subscriptionType"));


    useEffect(() => {

        fetch('http://localhost:8080/api/content/video/all/Local')
            .then(response => response.json())
            .then(data => {setVideos(data)

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

        // enter dummy videos
        // let videosTemp = [1,2,3];
        // setVideos(videosTemp);
        // setVideoNames(videosTemp);

    }, []);

    const handleClick = (event) => {

        let subscriptionType = localStorage.getItem("subscriptionType");
        console.log(subscriptionType);

        if (subscriptionType == "yearly" || subscriptionType == "monthly") {
            setCurrVideoName(event.currentTarget.textContent + ".mp4");
            console.log(currVideoName);
            return;
        }

        setOpen(true);
    }

    const handleMonthly = () => {

        let subscriptionType = 'monthly';
        let username = localStorage.getItem("username");
        let subscribedDate = new Date();

        axios.post(`http://localhost:8082/subscription?subscriptionType=${subscriptionType}&username=${username}&subscribedDate=${subscribedDate}`
        ).then((response) => {
            localStorage.setItem("subscriptionType", "monthly");
            setSubscriptionType("monthly");
            setOpen(false);
        });
        
    }

    const handleYearly = () => {
        let subscriptionType = 'yearly';
        let username = localStorage.getItem("username");
        let subscribedDate = new Date();

        axios.post(`http://localhost:8082/subscription?subscriptionType=${subscriptionType}&username=${username}&subscribedDate=${subscribedDate}`)
        .then((response) => {
            localStorage.setItem("subscriptionType", "monthly");
            setSubscriptionType("monthly");
            setOpen(false);
        });
    }

    const handleClose = () => {
        setOpen(false);
    }

    return (

    <MainCard>
        <Typography variant="h1">
            CINESTREAM
        </Typography>

        <Dialog
            open={open}
            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogContent dividers>
      
                <Grid container justifyContent="center" alignItems="center">
                    <Grid item xs={12}>
                        <Typography variant="h4" align='center'>
                            Choose a subscription type to continue
                        </Typography>
                    </Grid>

                    <Grid item xs={6}>
                        <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                            <Button variant="contained" color="success" sx={{ mt: 3 }} onClick={handleMonthly}>
                                Monthly
                            </Button>
                        </Box>
                    </Grid>

                    <Grid item xs={6}>
                        <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                            <Button variant="contained" color="success" sx={{ mt: 3 }} onClick={handleYearly}>
                                Yearly
                            </Button>
                        </Box>
                    </Grid>
                </Grid>
            </DialogContent>

            <DialogActions>
                <Button onClick={handleClose}>Cancel</Button>
            </DialogActions>
        </Dialog>

        {currVideoName !== "" ? (
        <video key={currVideoName} width="640" height="480" controls style={{margin: 'auto', display: 'block'}}>
            {/* <source src={"http://localhost:8080/video/" + currVideoName + "/Local"} type="video/mp4" /> */}
            <source src={"http://localhost:8080/api/content/video/" + currVideoName + "/Local"} type="video/mp4" />
        </video>
        ) : null}
        
        {videos.map((video, index) => (
            <div key={index}>
                <Box sx={{ minWidth: '100px', width: '100%', mt: '1%' }}>
                    <Button variant="contained" onClick={handleClick}>
                        {videoNames[index]}
                    </Button>
                </Box>
            </div>
        ))}

    </MainCard>
    );
};

export default Cinestream;
