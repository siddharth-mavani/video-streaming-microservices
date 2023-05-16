// assets
import { IconDashboard, IconBrandNetflix, IconBrandAmazon, IconBrandCarbon, IconCircleLetterH, IconBrandDisney} from '@tabler/icons';

// constant
const icons = { IconDashboard, IconBrandNetflix, IconBrandAmazon, IconBrandCarbon, IconBrandDisney};

// ==============================|| DASHBOARD MENU ITEMS ||============================== //

const dashboard = {
    id: 'dashboard',
    title: 'Dashboard',
    type: 'group',
    children: [
        {
            id: 'default',
            title: 'Dashboard',
            type: 'item',
            url: '/dashboard/default',
            icon: icons.IconDashboard,
            breadcrumbs: false
        },
        {
            id: 'cinestream',
            title: 'Cinestream',
            type: 'item',
            url: '/local',
            icon: icons.IconBrandCarbon,
            breadcrumbs: false
        },
        {
            id: 'hotstar',
            title: 'Hotstar',
            type: 'item',
            url: '/hotstar',
            icon: icons.IconBrandDisney,
            breadcrumbs: false
        },
        {
            id: 'netflix',
            title: 'Netflix',
            type: 'item',
            url: '/netflix',
            icon: icons.IconBrandNetflix,
            breadcrumbs: false
        },
        {
            id: 'prime',
            title: 'Prime',
            type: 'item',
            url: '/prime',
            icon: icons.IconBrandAmazon,
            breadcrumbs: false
        }
    ]
};

export default dashboard;
