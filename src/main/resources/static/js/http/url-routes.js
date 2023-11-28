const WHITE_LIST_URLS = new Set([
   '/auth/login',
   '/auth/logout',
   '/auth/register'
]);

export const urlRoute = (url) => {
    window.location = url;
};

