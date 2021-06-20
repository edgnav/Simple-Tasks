export class EventListModel {
  public id: number;
  public name: string;
  public description: string;

  constructor(name: string, description: string, id: number) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}

export class EventListResponse {
  public eventListDto: EventListModel[];
  public numberOfPages: number;
}
