import { lazy } from 'react';

// project imports
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';


// dashboard routing
const DashboardDefault = Loadable(lazy(() => import('views/dashboard/Default')));
const Cinestream = Loadable(lazy(() => import('views/cinestream')));
const Hotstar = Loadable(lazy(() => import('views/hotstar')));
const Netflix = Loadable(lazy(() => import('views/netflix')));
const Prime = Loadable(lazy(() => import('views/prime')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: <MainLayout />,
    children: [
        {
            path: '/',
            element: <DashboardDefault />
        },
        {
            path: 'dashboard',
            children: [
                {
                    path: 'default',
                    element: <DashboardDefault />
                }
            ]
        },
        {
            path: 'local',
            children: [
                {
                    path: '',
                    element: <Cinestream />
                }
            ]
        },
        {
            path: 'hotstar',
            children: [
                {
                    path: '',
                    element: <Hotstar /> 
                }
            ]
        },
        {
            path: 'netflix',
            children: [
                {
                    path: '',
                    element: <Netflix /> 
                }
            ]
        },
        {
            path: 'prime',
            children: [
                {
                    path: '',
                    element: <Prime />
                }
            ]
        }

        

    ]
};

export default MainRoutes;
