export function navigate(page: string) {
  location.hash = page;
}

export function getRoute() {
  return location.hash.replace("#", "") || "login";
}